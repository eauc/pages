(ns pages.components.activity-footer
  (:require [cljs-styled-components.reagent :refer [defstyled]]
            [pages.history :refer [nav!]]
            [reagent.core :as reagent]))

(defstyled footer :div
  {;; position
   :position "fixed"
   :bottom 0
   :left 0
   :right 0
   ;; box
   :height 0
   :padding "0 0.5em"
   ;; row container layout
   :display "flex"
   :flex-direction "row"
   :justify-content "flex-end"})

(defstyled action :button
  { ;; box
   :width "2em"
   :height "2em"
   :padding "0.5em"
   :border-radius "1em"
   :margin-top "-2.5em"
   :box-shadow "2px 2px 5px 0px rgba(102, 102, 102, 0.5)"
   ;; style
   :background "#00CC33"
   :color "white"
   :font-weight "bold"
   :font-size "1.5em"
   :line-height "1em"
   "&:active" {:background-color "#00AAAA"}
   "&:focus" {:outline "none"}})

(defn activity-footer
  [page]
  (fn [page]
    [footer
     [action
      {:onClick #(nav! (str "/page/" (inc page)))}
      ">"]]))
