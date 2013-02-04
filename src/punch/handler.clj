(ns punch.handler
  (:use compojure.core)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]))

(def blank-catalog (binding [*read-eval* false] ; not necessary, but paranoid
                     (slurp (clojure.java.io/resource "blank-catalog.json"))))

(defroutes app-routes
  (GET "/" []
       {:headers {"Content-Type" "application/json"}
        :body    blank-catalog})
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))
