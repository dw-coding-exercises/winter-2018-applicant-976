(ns my-exercise.search
  (:require [clj-http.client :as client]
            [hiccup.page :refer [html5]]))

(defn sanitize-params [params]
  (def clean-city (clojure.string/lower-case (clojure.string/replace (get params "city") #" " "_")))
  (def clean-state (clojure.string/lower-case (get params "state")))
  {:street (get params "street")
    :street-2 (get params "street-2")
    :city clean-city
    :state clean-state
    :zip (get params "zip")})

(defn create-ocd-ids [location]
  (def ocd-state (str "ocd-division/country:us/state:" (get location :state)))
  (def ocd-city (str ocd-state "/place:" (get location :city)))
  (str ocd-state "," ocd-city))

(defn get-upcoming-elections [ids]
  (def response (client/get
    (str "https://api.turbovote.org/elections/upcoming?district-divisions=" ids) {:accept :json}))
    (get response :body))

(defn display-elections [election]
  (html5
    [:p "Upcoming Elections in Your Area"]

      [:p (get election "description")]))

(defn search [request]
  (def location (sanitize-params (get request :form-params)))
  (def ocd-ids (create-ocd-ids location))
  (def upcoming-elections (get-upcoming-elections ocd-ids))
  (display-elections upcoming-elections))
