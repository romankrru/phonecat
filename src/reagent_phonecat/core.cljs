(ns reagent-phonecat.core
    (:require [reagent.core :as reagent :refer [atom]]))

(enable-console-print!)

(defonce app-state (atom {:text "Hello world!"}))

(def static content
  "Some sample, statically defined DOM content")

(defn app []
  [:div
   [:h1 (:text @app-state)]
   [:h3 "Edit this and watch it change!"]])

(reagent/render-component [app]
                          (. js/document (getElementById "app")))

(defn on-js-reload [])
