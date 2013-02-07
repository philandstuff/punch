(ns punch.handler
  (:use compojure.core)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.mock.request :refer [request]]
            [cheshire.core :refer [generate-string]]
            [punch.catalog :refer [catalog-for]]))

(defn to-json-data-map [catalog]
  {:document_type "Catalog"
   :metadata      {:api_version 1}
   :data          catalog})

(defroutes app-routes
  (GET "/:hostname" [hostname]
       {:headers {"Content-Type" "application/json"}
        :body    (generate-string (to-json-data-map (catalog-for hostname)))})
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))

(defn -main [& args]
  (let [hostname (first args)]
    (print (:body (app (request :get (str "/" hostname)))))))
