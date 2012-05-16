(ns neobox.core
  (:require [clojurewerkz.neocons.rest               :as neo]
            [clojurewerkz.neocons.rest.nodes         :as nodes]
            [clojurewerkz.neocons.rest.relationships :as relationships]))

(neo/connect! "http://localhost:7474/db/data/")

#_(def ^:private
  root
  (nodes/get 0))


;;; Reference Nodes

(defn- get-ref [type]
  "Return the reference node for the given type if it exists."
  (second (nodes/traverse (:id root)
                          :relationships [{:direction :out
                                           :type type}])))

(defn- ensure-ref! [type]
  "Return the reference node for the given type if it exists or create it if it does not."
  (if-let [existing (get-ref type)]    
    existing
    (let [created (nodes/create)]
      (relationships/create root created type)
      created)))


;;; Indices

(defn- get-idx [name]
  "Return the index with the given name if it exists."
  (first (filter #(= (:name %) name)
                 (nodes/all-indexes))))

(defn- ensure-idx! [name]
  "Return the named index if it exists or create it if it does not."
  (if-let [existing (get-idx name)]
    existing
    (nodes/create-index name)))


;;; Domains

(def ^:private
  domains-ref
  (ensure-ref! "domains"))

(def ^:private
  domains-idx
  (ensure-idx! "domains"))

#_(defn create-domain! [name]
  (let [domain (nodes/create {:name name})]
    (nodes/add-to-index (:id domain) domain-index :name name)
    (relationships/create domains-ref-node domain :domain)))

#_(defn find-domain [name]
  (nodes/find-one domain-index :name name))


;;; Categories

(def ^:private
  categories-ref
  (ensure-ref! "categories"))

(def ^:private
  categories-idx
  (ensure-idx! "categories"))

#_(defn create-category-core! [properties]
  (let [class-node (nodes/create properties)]
    (relationships/create categories-ref-node class-node :class)
    class-node))

(defn create-category-usage! []
  #_(relationships/create from to :instance))


;;; Items

(def ^:private
  items-ref
  (ensure-ref! "items"))

(def ^:private
  items-idx
  (ensure-idx! "items"))

(defn create-item-core! []
  )

(defn create-item-usage! []
  )




