(ns pages.app
  (:require [cljs-styled-components.reagent :refer [defglobalstyle]]
            [pages.components.activity :refer [activity]]
            [pages.db]
            [reagent.core :as reagent]
            [re-frame.core :as rf]))

(enable-console-print!)

(println "Coucouc CLJS")

(js/console.log "Coucouc JS")

(defglobalstyle
  global-styles
  {"*" {:margin 0
        :padding 0
        :border 0
        :font-family "'Roboto', sans-serif"}
   :body {:overflow "hidden"}})

(defn app
  []
  (let [pages (rf/subscribe [:pages.db/pages])
        current-page (rf/subscribe [:pages.db/current-page])]
    (fn []
      ;; (println "render-list" @pages @current-page)
      [:<>
       [global-styles]
       (doall
         (for [[n page] (take 3
                              (drop (max 0 (- @current-page 1))
                                    (map-indexed vector @pages)))]
           ^{:key page}
           [activity page (> n @current-page)]))])))

(defn ^:dev/after-load mount-app!
  []
  (println "mount-app!")
  (rf/clear-subscription-cache!)
  (reagent/render
    [app]
    (js/document.getElementById "app")))
