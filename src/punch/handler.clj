(ns punch.handler
  (:use compojure.core)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.mock.request :refer [request]]))

(def blank-catalog (binding [*read-eval* false] ; not necessary, but paranoid
                     (slurp (clojure.java.io/resource "blank-catalog.json"))))

(defn catalog-for [hostname]
  blank-catalog)

(defroutes app-routes
  (GET "/" []
       {:headers {"Content-Type" "application/json"}
        :body    (catalog-for "localhost")})
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))

(defn -main [& args]
  (print (:body (app (request :get "/")))))
