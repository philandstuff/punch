(ns punch.test.handler
  (:use clojure.test
        ring.mock.request  
        punch.handler))

(deftest test-app
  (testing "main route"
    (let [response (app (request :get "/"))]
      (is (= (get-in response [:headers "Content-Type"]) "application/json"))
      (is (= (:status response) 200))
      #_(is (= (:body response) "Hello World"))))
  
  (testing "not-found route"
    (let [response (app (request :get "/invalid"))]
      (is (= (:status response) 404)))))