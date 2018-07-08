(ns reagent-phonecat.core
    (:require [reagent.core :as reagent :refer [atom]]
              [clojure.string :as str]))

(enable-console-print!)

(def hardcoded-phones-data
  [{:name "Nexus S"
    :description "Fast just got fater"}
   {:name "Motorola XOOM with WI-FI"
    :description "The Next, Next Generation Tablet."}])

(defn update-search [state new-search]
  (assoc state :search new-search))

(defn matches-search?
  "Determines if a phone item matches a text query."
  [search data]
  (let [qp (-> search
               (or "")
               str/lower-case
               re-pattern)]
    (->> (vals data)
         (filter string?)
         (map str/lower-case)
         (some #(re-find qp %)))))

(defonce state (atom {:phones hardcoded-phones-data
                      :search ""
                      :order-prop :name}))

(def order-prop-state (reagent/cursor state [:order-prop]))

(declare <phones-list>
         <phone-item>)

(defn <order-prop-select> []
  [:select {:value @order-prop-state
            :on-change #(reset!
                               order-prop-state
                               (-> %
                                   .-target
                                   .-value
                                   keyword))}
   [:option {:value :name} "Alphabetical"]
   [:option {:value :age} "Newest"]])

(defn <search-cpnt> [search]
  [:span
   "Search:"
   [:input {:type "text"
            :value search
            :on-change (fn [e]
                         (swap! state
                                update-search
                                (-> e .-target .-value)))}]])

(defn <top-cpnt> []
  (let [{:keys [phones search]} @state]
    [:div.container-fluid
     [:div.row
       [:div.col-4
        [<search-cpnt> search]
        [:br] [:br] "Sort by: "
        [<order-prop-select>]]
       [:div.col-8 [<phones-list> phones search @order-prop-state]]]]))

(defn <phones-list>
  [phones-list search order-prop]
  [:div.container-fluid
   [:ul
    (for [phone (->> phones-list
                     (filter #(matches-search? search %))
                     (sort-by order-prop))]
      ^{:key (:name phone)}
      [<phone-item> phone])]])

(defn <phone-item>
  [{:keys [name description]}]
  [:li.phone-item
   [:span name]
   [:p description]])

(reagent/render-component [<top-cpnt>]
                          (. js/document (getElementById "app")))

(defn on-js-reload [])
