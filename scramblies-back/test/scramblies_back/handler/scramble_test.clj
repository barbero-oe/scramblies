(ns scramblies-back.handler.scramble_test
  (:require [clojure.test :refer :all]
            [integrant.core :as ig]
            [ring.mock.request :as mock]
            [duct.core :as duct]
            [ataraxy.core :as ataraxy]))

(defn load-config []
  (-> (duct/resource "scramblies_back/config.edn")
      (duct/read-config)
      :duct.profile/base
      :duct.router/ataraxy))

(defn add-handler [config key]
  (assoc config :handlers {key (ig/init-key key {})}))

(defn configure [key]
  (-> (load-config)
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