(ns reagent-phonecat.core
    (:require [reagent.core :as reagent :refer [atom]]))

(enable-console-print!)

(defonce app-state (atom {:text "Hello world!"}))

(declare <phones-list>
         <phone-item>)

(def hardcoded-phones-data
  [{:name "Nexus S"
    :description "Fast just got fater"}
   {:name "Motorola XOOM with WI-FI"
    :description "The Next, Next Generation Tablet."}])


(defn <phone-list>
  [phones-list]
  [:div.container-fluid
   [:ul
    (for [phone phones-list]
      ^{:key (:name phone)}
      [<phone-item> phone])]])

(defn <phone-item>
  [{:keys [name description]}]
  [:li.phone-item
   [:span name]
   [:p description]])

(reagent/render-component [<phone-list> hardcoded-phones-data]
                          (. js/document (getElementById "app")))

(defn on-js-reload [])
