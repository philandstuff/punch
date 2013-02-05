(ns punch.handler
  (:use compojure.core
        [clojure.java.io :only [reader resource]])
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.mock.request :refer [request]]
            [cheshire.core :refer [generate-string parse-string]]))

(def blank-catalog (binding [*read-eval* false] ;; turn off EvalReader
                     (read (java.io.PushbackReader. (reader (resource "blank-catalog.edn"))))))

(defn catalog-for [hostname]
  blank-catalog)

(defroutes app-routes
  (GET "/" []
       {:headers {"Content-Type" "application/json"}
        :body    (generate-string (catalog-for "localhost"))})
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))

(defn -main [& args]
  (print (:body (app (request :get "/")))))
