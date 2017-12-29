(ns my-exercise.search)

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

(defn search [request]
  (def location (sanitize-params (get request :form-params)))
  (def ocd-ids (create-ocd-ids location))
  (println ocd-ids)
  "testing")
