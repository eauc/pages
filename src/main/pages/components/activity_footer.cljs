(ns pages.components.activity-footer
  (:require [pages.history :refer [nav!]]
            [reagent.core :as reagent]))

(defn activity-footer
  [page]
  [:div.footer
   [:button.action
    {:on-click #(nav! (str "/page/" (inc page)))}
    ">"]])
