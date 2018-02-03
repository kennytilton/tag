(ns tiltontec.webmx.example.ticktock
  (:require [clojure.string :as str]
            [tiltontec.cell.core :refer-macros [c? c?once] :refer [c-in]]
            [tiltontec.model.core
             :refer [matrix mx-par md-get <mget mset!> md-reset! mxi-find mxu-find-name] :as md]
            [tiltontec.webmx.gen :refer [evt-tag target-value] :refer-macros [h1 input div span]]))

(declare clock time-color color-input)


(defn matrix-build! []
  (md/make ::ticktock
    :mx-dom (c?once (md/with-par me
                      [(div {}
                         (h1 {} "Hello, world. 'Tis now....")
                         (clock)
                         (color-input)
                         (div {:style {:background-color "yellow"
                                       :display "flex"
                                       :min-height "96px"
                                       :flex-direction "row"
                                       :flex-wrap "wrap"

                                       ;;:gap "24px"
                                       :justify-content "space-between"
                                       }}
                               (map #(span {:style {:background "cyan"
                                                    :flex-basis "content"}}{}
                                       (str % ",")) (str/split "four score and seven years ago today our forefathers brought forth on this continent" " ")))
                         )]))))

(defn clock []
  (div {:class   "example-clock"
      :style   (c? (str "color:" (<mget (mxu-find-name me :timecolor) :value)))

      :tick (c-in false :ephemeral? true)
      :ticker (c? (js/setInterval #(mset!> me :tick true) 1000))

      :content (c? (if (<mget me :tick)
                     (-> (js/Date.)
                         .toTimeString
                         (str/split " ")
                         first)
                     "*checks watch*"))}))

(defn color-input []
  (div {:class "color-input"}
    "Time color: "
    (input {:name     :timecolor
            :tag/type "text"
            :value    (c-in "#0ff")
            :onchange #(mset!> (evt-tag %)
                                  :value (target-value %))})))