(ns pages.components.activity-header
  (:require [pages.history :refer [nav!]]
            [reagent.core :as reagent]))

(defn activity-header
  [page opened? on-open]
  (println "render-header" page opened?)
  [:div.header
   [:button.back
    {:on-click #(do (on-open :menu)
                    (.stopPropagation %))}
    "M"]
   [:ul.menu
    {:class (if (= :menu opened?) "open")}
    (doall
      (for [i (range page)]
        ^{:key i} [:li (str "Menu " i)]))]
   [:div.title (str "Page " page)]
   [:button.actions-toggle
    {:on-click #(do (on-open :actions)
                    (.stopPropagation %))}
    "A"]
   [:ul.actions
    {:class (if (= :actions opened?) "open")}
    (doall
      (for [i (range page)]
        ^{:key i} [:li (str "Action " i)]))]])
