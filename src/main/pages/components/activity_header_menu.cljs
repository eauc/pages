(ns pages.components.activity-header-menu
  (:require [cljs-styled-components.reagent :refer [clj-props defstyled]]
            [pages.history :refer [nav!]]
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
   "&:active" {:background-color "#CCC"}
   "&:focus" {:outline "none"}})

(defstyled menu :ul
  { ;; position
   :position "fixed"
   :top "2em"
   :left #(if (= "left" (aget % "side")) 0 "auto")
   :right #(if (= "right" (aget % "side")) 0 "auto")
   ;; box
   :box-shadow #(if (= "left" (aget % "side"))
                    "2px 2px 5px 0px rgba(102, 102, 102, 0.5)"
                    "-2px 2px 5px 0px rgba(102, 102, 102, 0.5)")
   ;; style
   :font-size "1.5em"
   :line-height "2em"
   :background-color "white"
   :list-style-type "none"
   ;; animation
   :opacity #(if (aget % "opened?") 1 0)
   :pointer-events #(if (aget % "opened?") "auto" "none")
   :transition "all 0.4s ease"
   "& li" {:padding "0 0.5em"}
   "& li:active" {:background-color "#CCC"}})

(def menu* (:react-component (meta menu)))

(defn activity-header-menu
  [side icon opened? on-open entries]
  (println "render-header-menu" icon opened?)
  [:<>
   [menu-toggle
    {:onClick #(do (on-open)
                   (.stopPropagation %))}
    icon]
   [:> menu*
    {:side side
     :opened? opened?}
    entries]])
