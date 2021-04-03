(ns scramblies-front.views
  (:require
    [re-frame.core :as re-frame]
    [scramblies-front.subs :as subs]
    [scramblies-front.events :as events]
    [stylefy.core :as stylefy :refer [use-style]]))

(def center
  {:margin      "auto"
   :padding-top "4em"
   :width       "50ch"})

(def vertical-form
  {:display         "flex"
   :flex-direction  "column"
   ::stylefy/manual [:& [:* {:margin-bottom ".5em"}]]})

(def input-style
  {:font-size     "1.5em"
   :border        "2px solid #d8d8d8"
   :border-radius "0.2em"
   :outline       "none"
   :height        "1.5em"
   ::stylefy/mode {:focus {:border "2px solid #74c6de"}}})

(def button-style
  {:display          "inline-block"
   :background-color "#31b0d5"
   :border           "1px solid #269abc"
   :border-radius    "0.2em"
   :font-size        "1.5em"
   :height           "1.5em"
   :cursor           "pointer"
   :color            "#fff"
   :transition       "background-color 150ms ease-in-out"
   :outline          "none"
   ::stylefy/mode    {:hover {:background-color "#269abc"
                              :border-color     "#1b6d85"}}})

(defn user-input [{:keys [on-change value]}]
  [:input (use-style input-style
                     {:on-change #(on-change (-> % .-target .-value))
                      :value     value})])

(def message-info
  {:display         "flex"
   :font-size       "2em"
   :justify-content "center"})


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
   [:button (use-style button-style {:on-click on-scramble}) "Scramble"]
   [:div (use-style message-info)
    (case scrambles
      true [:span "Scrambles!"]
      false [:span "Does not scrambles"]
      nil)]])

(defn main-style [color]
  {:background-color color
   :transition       "background-color 500ms ease-in-out"
   :height           "100vh"
   :font-family      "sans-serif"})

(defn main-panel []
  (let [string (re-frame/subscribe [::subs/string])
        target (re-frame/subscribe [::subs/target])
        scrambles (re-frame/subscribe [::subs/scrambles])
        error (re-frame/subscribe [::subs/error])
        color (re-frame/subscribe [::subs/color])]
    [:main (use-style (main-style @color))
     [:div (use-style center)
      [scramble-form
       {:string           @string
        :target           @target
        :scrambles        @scrambles
        :on-scramble      #(re-frame/dispatch [::events/query-scrambliness])
        :on-string-change #(re-frame/dispatch [::events/change-string %])
        :on-target-change #(re-frame/dispatch [::events/change-target %])}]
      [:div (use-style message-info)
       [:span @error]]]]))
