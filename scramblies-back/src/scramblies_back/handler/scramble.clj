(ns scramblies-back.handler.scramble
  (:require [ataraxy.response :as response]
            [integrant.core :as ig]
            [scramblies-back.domain.scramble :refer [scramble?]]))

(defmethod ig/init-key :scramblies-back.handler/scramble [_ _]
  (fn [{[_ string target] :ataraxy/result}]
    [::response/ok {:scramble (scramble? string target)}]))
