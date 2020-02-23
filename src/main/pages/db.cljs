(ns pages.db
  (:require [clojure.edn :as edn]
            [re-frame.core :as rf]))

(def save-state
  (rf/->interceptor
    :id      ::save-state
    :after  (fn [{{:keys [db]} :effects :as context}]
              (.setItem js/localStorage "app-state" (pr-str db))
              context)))

(rf/reg-event-db
  ::init
  (fn [_ [_ state]]
    state))

(def load-state
  (rf/dispatch
    [::init
     (merge
       {::pages [0]
        ::current-page 0}
       (edn/read-string
         (.getItem js/localStorage "app-state")))]))

(rf/reg-event-db
  ::set-page
  [save-state]
  (fn [db [_ page]]
    (let [{pages ::pages current-page ::current-page} db]
      (if (= page (nth pages current-page))
        db
        (if (and (< 0 current-page)
                 (= page (nth pages (dec current-page))))
          (update db ::current-page dec)
          (if (and (< current-page (dec (count pages)))
                   (= page (nth pages (inc current-page))))
            (update db ::current-page inc)
            (assoc db
                   ::pages (conj (subvec pages 0 (inc current-page)) page)
                   ::current-page (inc current-page))))))))

(rf/reg-sub
  ::pages
  (fn [db _]
    (::pages db)))

(rf/reg-sub
  ::current-page
  (fn [db _]
    (::current-page db)))
