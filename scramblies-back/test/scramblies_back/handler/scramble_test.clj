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
  (let [request (mock/request type url)
        result (handlers request)]
    (doall (map clojure.pprint/pprint [type url request result]))
    result))

(deftest scramble-handler-test
  (testing "handles scramble request"
    (let [config (configure :scramblies-back.handler/scramble)
          get-body #(:body (request config :get %))]
      (is (= {:scramble true} (get-body "/scramble/dlrow/world")))
      (is (= {:scramble false} (get-body "/scramble/foo/boo"))))))