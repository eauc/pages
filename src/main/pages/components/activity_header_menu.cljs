(ns pages.components.activity-header-menu
  (:require [cljss.reagent :as css :refer-macros [defstyled]]
            [reagent.core :as reagent]))

(defstyled menu-toggle :button
  {;; box
   :width "2em"
   :height "2em"
   :padding "0.5em"
   :border-radius "1em"
   :margin-right "0.2em"
   ;; style
   :background "transparent"
   :font-weight "bold"
   :font-size "1.2em"
   :&:active {:background-color "#CCC"}
   :&:focus {:outline "none"}})

(defstyled menu :ul
  { ;; position
   :position "fixed"
   :top "2em"
   :left? {:left 0
           ;; box
           :box-shadow "2px 2px 5px 0px rgba(102, 102, 102, 0.5)"}
   :right? {:right 0
            ;; box
            :box-shadow "-2px 2px 5px 0px rgba(102, 102, 102, 0.5)"}
   ;; style
   :font-size "1.5em"
   :line-height "2em"
   :background-color "white"
   :list-style-type "none"
   ;; animation
   :opacity 0
   :pointer-events "none"
   :transition "all 0.4s ease"
   :opened? {:opacity 1
             :pointer-events "auto"}
   "li" {:padding "0 0.5em"}
   "li:active" {:background-color "#CCC"}})

(defn activity-header-menu
  [side icon opened? on-open items]
  ;; (println "render-header" page opened?)
  [:<>
   [menu-toggle
    {:on-click #(do (on-open)
                    (.stopPropagation %))}
    icon]
   [menu
    {:left? (= :left side)
     :right? (= :right side)
     :opened? opened?}
    items]])
