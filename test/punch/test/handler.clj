(ns punch.test.handler
  (:use clojure.test
        ring.mock.request  
        punch.handler)
  (:require [cheshire.core :refer [parse-string]]))

(defn valid-json-catalog? [json-data]
  (and
   (= (get-in json-data ["metadata" "api_version"]) 1)
   (= (get json-data "document_type") "Catalog")))

(deftest test-app
  (testing "main route"
    (let [response (app (request :get "/"))
          parsed-json (parse-string (:body response))]
      (is (= (get-in response [:headers "Content-Type"]) "application/json"))
      (is (= (:status response) 200))
      (is (valid-json-catalog? parsed-json))))
  
  (testing "not-found route"
    (let [response (app (request :get "/invalid"))]
      (is (= (:status response) 404)))))