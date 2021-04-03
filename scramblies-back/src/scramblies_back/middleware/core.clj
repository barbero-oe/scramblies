(ns scramblies-back.middleware.core
  (:require [integrant.core :as ig]
            [ring.middleware.cors :refer [wrap-cors]]
            [scramblies-back.domain.scramble :refer [scramble?]]))


(defmethod ig/init-key :scramblies-back.middleware/cors [_ {front-url :front-url}]
  (let [pattern (re-pattern front-url)]
    (fn [handler]
      (wrap-cors handler
                 :access-control-allow-origin [pattern]
                 :access-control-allow-methods [:get :put :post :delete]))))
