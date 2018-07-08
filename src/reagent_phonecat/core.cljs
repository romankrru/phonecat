(ns reagent-phonecat.core
    (:require [reagent.core :as reagent :refer [atom]]))

(enable-console-print!)

(defonce app-state (atom {:text "Hello world!"}))

(def static-content
  "Some sample, statically defined DOM content"
  [:ul#phones-list
   [:li.phone-item
    [:span "Nexus S"]
    [:p "Fast just got faster with Nexus S"]]
   [:li.phone-item
    [:span "Motorola XOOM with WI-FI"]
    [:p "The Next, Next Generation tablet."]]])


(reagent/render-component static-content
                          (. js/document (getElementById "app")))

(defn on-js-reload [])
