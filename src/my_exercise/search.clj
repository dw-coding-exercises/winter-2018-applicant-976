(ns my-exercise.search)

(defn search [request]
  (def form-data (get request :form-params))
  (println form-data)
  "testing")
