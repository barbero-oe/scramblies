(ns scramblies-back.handler.scramble
  (:require [integrant.core :as ig]
            [scramblies-back.domain.scramble :refer [scramble?]]))

(defn alpha? [value] (re-matches #"[a-z]+" value))

(defmethod ig/init-key :scramblies-back.handler/scramble [_ _]
  (fn [{[_ string target] :ataraxy/result}]
    (if (every? alpha? [string target])
      {:status 200, :body {:scramble (scramble? string target)}}
      {:status 400, :body {:error "Only lower-case english letters are allowed."}})))
