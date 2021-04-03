(ns scramblies-front.styles
  (:require [stylefy.core :as stylefy]))

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

(def message-info
  {:display         "flex"
   :font-size       "2em"
   :justify-content "center"})

(defn main-style [color]
  {:background-color color
   :transition       "background-color 500ms ease-in-out"
   :height           "100vh"
   :font-family      "sans-serif"})

