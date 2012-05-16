(ns neobox.core
  (:require [clojurewerkz.neocons.rest       :as neo]
            [clojurewerkz.neocons.rest.nodes :as nodes]))

(neo/connect! "http://localhost:7474/db/data/")


