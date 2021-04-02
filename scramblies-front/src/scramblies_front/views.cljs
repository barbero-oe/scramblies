(ns scramblies-front.views
  (:require
    [re-frame.core :as re-frame]
    [scramblies-front.subs :as subs]
    [stylefy.core :refer [use-style]]))

(def center-controls
  {:margin         "auto"
   :width          "50ch"
   :display        "flex"
   :flex-direction "column"})

(defn scramble-form [{:keys [string
                             target
                             scrambles
                             on-scramble
                             on-string-change
                             on-target-change]}]
  [:div (use-style center-controls)
   [:input {:on-change on-string-change :value string}]
   [:input {:on-change on-target-change :value target}]
   [:button {:on-click on-scramble} "Scramble"]
   (case scrambles
     true [:span "Scrambles!"]
     false [:span "Does not scrambles"]
     nil)])

(defn main-panel []
  [scramble-form
   {:string      "foo"
    :target      "boo"
    :on-scramble #(println "scramble!")}])