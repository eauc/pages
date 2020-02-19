(ns pages.app
  (:require [pages.components.activity :refer [activity]]
            [pages.db]
            [reagent.core :as reagent]
            [re-frame.core :as rf]))

(enable-console-print!)

(println "Coucouc CLJS")

(js/console.log "Coucouc JS")

(defn app
  []
  (let [pages (rf/subscribe [:pages.db/pages])
        current-page (rf/subscribe [:pages.db/current-page])]
    (fn []
      ;; (println "render-list" @pages @current-page)
      [:<>
       (doall
         (for [[n page] (take 3
                              (drop (max 0 (- @current-page 1))
                                    (map-indexed vector @pages)))]
           ^{:key page}
           [activity page (> n @current-page)]))])))

(defn ^:dev/after-load mount-app!
  []
  (println "mount-app!")
  (reagent/render
    [app]
    (js/document.getElementById "app")))

(mount-app!)
