(ns punch.handler
  (:use compojure.core)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.mock.request :refer [request]]
            [cheshire.core :refer [generate-string parse-string]]))

(def blank-catalog (binding [*read-eval* false] ; not necessary, but paranoid
                     (slurp (clojure.java.io/resource "blank-catalog.json"))))

(defn catalog-for [hostname]
  (parse-string blank-catalog true))

(defroutes app-routes
  (GET "/" []
       {:headers {"Content-Type" "application/json"}
        :body    (generate-string (catalog-for "localhost"))})
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))

(defn -main [& args]
  (print (:body (app (request :get "/")))))
