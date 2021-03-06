(ns pages.components.activity-header
  (:require [cljss.reagent :as css :refer-macros [defstyled]]
            [pages.components.activity-header-menu :refer [activity-header-menu]]))

(defstyled header :div
  {;; grid item layout
   :grid-area "header"
   :align-self "stretch"
   :justify-self "stretch"
   ;; box
   :box-shadow "0 2px 5px 0px rgba(102, 102, 102, 0.5)"
   ;; style
   :font-weight "bold"
   ;; row container layout
   :display "flex"
   :flex-direction "row"
   :justify-content "start"
   :align-items "center"})

(defstyled title :div
  {:flex-grow 1
   :font-size "1.5em"})

(defn activity-header
  [page opened? on-open]
  ;; (println "render-header" page opened?)
  [header
   [activity-header-menu
    :left "M" (= :menu opened?) #(on-open :menu)
    (doall
      (for [i (range page)]
        ^{:key i} [:li (str "Menu " i)]))]
   [title (str "Page " page)]
   [activity-header-menu
    :right "A" (= :actions opened?) #(on-open :actions)
    (doall
      (for [i (range page)]
        ^{:key i} [:li (str "Action " i)]))]])
