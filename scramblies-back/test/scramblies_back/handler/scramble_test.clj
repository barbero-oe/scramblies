(ns scramblies-back.handler.scramble_test
  (:require [clojure.test :refer :all]
            [integrant.core :as ig]
            [ring.mock.request :as mock]
            [duct.core :as duct]
            [ataraxy.core :as ataraxy]))

(defn load-ataraxy-config []
  (-> (duct/resource "scramblies_back/config.edn")
      (duct/read-config)
      :duct.profile/base
      :duct.router/ataraxy))

(defn add-cors-middleware [config]
  (assoc config :middleware
                {:cors (ig/init-key :scramblies-back.middleware/cors {:front-url ""})}))

(defn add-handler [config key]
  (assoc config :handlers {key (ig/init-key key {})}))

(defn configure [key]
  (-> (load-ataraxy-config)
      (add-cors-middleware)
      (add-handler key)
      (ataraxy/handler)))

(defn request [handlers type url]
  (handlers (mock/request type url)))

(deftest scramble-handler-test
  (testing "handles scramble request"
    (let [config (configure :scramblies-back.handler/scramble)
          get-body #(:body (request config :get %))]
      (is (= {:scramble true} (get-body "/scramble/dlrow/world")))
      (is (= {:scramble false} (get-body "/scramble/foo/boo"))))))

(deftest scramble-handler-test
  (testing "checks the parameters are alphabetic"
    (let [config (configure :scramblies-back.handler/scramble)
          http-get #(request config :get %)
          error {:status 400
                 :body   {:error "Only lower-case english letters are allowed."}}]
      (is (= error (http-get "/scramble/hello2/world")))
      (is (= error (http-get "/scramble/foo/heLLo"))))))
