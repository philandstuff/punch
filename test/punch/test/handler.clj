(ns punch.test.handler
  (:use clojure.test
        ring.mock.request  
        punch.handler)
  (:require [cheshire.core :refer [parse-string]]))

(deftest test-app
  (testing "main route"
    (let [response (app (request :get "/"))
          parsed-json (parse-string (:body response))]
      (is (= (get-in response [:headers "Content-Type"]) "application/json"))
      (is (= (:status response) 200))
      (is (= (get-in parsed-json ["metadata" "api_version"]) 1))
      (is (= (get-in parsed-json ["document_type"]) "Catalog"))))
  
  (testing "not-found route"
    (let [response (app (request :get "/invalid"))]
      (is (= (:status response) 404)))))