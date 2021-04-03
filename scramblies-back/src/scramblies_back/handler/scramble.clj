(ns scramblies-back.handler.scramble
  (:require [integrant.core :as ig]
            [scramblies-back.domain.scramble :refer [scramble?]]))

(defmethod ig/init-key :scramblies-back.handler/scramble [_ _]
  (fn [{[_ string target] :ataraxy/result}]
    {:status 200, :body {:scramble (scramble? string target)}}))
