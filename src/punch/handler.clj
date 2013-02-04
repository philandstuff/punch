(ns punch.handler
  (:use compojure.core)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]))

(def blank-catalog (slurp "blank-catalog.json"))

(defroutes app-routes
  (GET "/" []
       {:headers {"Content-Type" "application/json"}
        :body    blank-catalog})
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))
