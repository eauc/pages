(ns pages.routes
  (:require [pages.db]
            [re-frame.core :as rf]
            [reitit.frontend :as frontend]
            [reitit.frontend.easy :as easy]
            [reitit.coercion.spec :as spec]))

(def routes
  [["/" ::main]
   ["/pages/:page-id"
    {:name ::page
     :parameters {:path {:page-id int?}}}]])

(defn init!
  []
  (easy/start!
    (frontend/router
      routes
      {:data {:coercion spec/coercion}})
    (fn [match]
      (let [page-id (get-in match [:parameters :path :page-id] 0)]
        (rf/dispatch [:pages.db/set-page page-id])))
    {:use-fragment false}))
