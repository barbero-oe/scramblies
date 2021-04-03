(ns scramblies-front.views
  (:require
    [re-frame.core :as re-frame]
    [scramblies-front.subs :as subs]
    [scramblies-front.events :as events]
    [stylefy.core :refer [use-style]]))

(def center-controls
  {:margin "auto"
   :width  "50ch"})

(def vertical-form
  {:display        "flex"
   :flex-direction "column"})

(defn user-input [{:keys [on-change value]}]
  [:input {:on-change #(on-change (-> % .-target .-value))
           :value     value}])

(defn scramble-form [{:keys [string
                             target
                             scrambles
                             on-scramble
                             on-string-change
                             on-target-change]}]
  [:div (use-style vertical-form)
   [user-input {:on-change on-string-change
                :value     string}]
   [user-input {:on-change on-target-change
                :value     target}]
   [:button {:on-click on-scramble} "Scramble"]
   (case scrambles
     true [:span "Scrambles!"]
     false [:span "Does not scrambles"]
     nil)])

(defn main-panel []
  (let [string (re-frame/subscribe [::subs/string])
        target (re-frame/subscribe [::subs/target])
        scrambles (re-frame/subscribe [::subs/scrambles])
        error (re-frame/subscribe [::subs/error])]
    [:div (use-style center-controls)
     [scramble-form
      {:string           @string
       :target           @target
       :scrambles        @scrambles
       :on-scramble      #(re-frame/dispatch [::events/query-scrambliness])
       :on-string-change #(re-frame/dispatch [::events/change-string %])
       :on-target-change #(re-frame/dispatch [::events/change-target %])}]
     [:div @error]]))
