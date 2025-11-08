(ns conj-2025.core
  (:require [goog.a11y.aria :as aria]
            [goog.date :as gdate]
            [goog.dom :as gdom]
            [goog.events :as gevents]
            [goog.style :as gstyle])
  (:import [goog.i18n DateTimeFormat]
           [goog.a11y.aria State]))

(def button (gdom/get "click-me"))

(gstyle/setStyle button "font-size" "100px")
(gstyle/setStyle button "border-radius" "25px")
(gstyle/setStyle button
  #js {:margin-left "50px"
       :margin-top  "25px"
       :text-stroke "1px white"})

(defn update-button [s]
  (gdom/setTextContent button s))

(gevents/listen button
  #js ["click", "mouseenter" "mouseleave"]
  (fn [e]
    (case (. e -type)
      "mouseenter" (update-button "HI!")
      "mouseleave" (update-button "BYE!")
      "click"      (update-button "OUCH!"))))

(def now (gdate/DateTime.))

(def interval (goog.date.Interval. 0 0 3 1))
(def later (.clone now))
(.add later interval)
(.log js/console later)

(def fmt (DateTimeFormat. "EEEE MMMM d, y"))
(.log js/console (.format fmt now))

(aria/setLabel button "This button does something really cool!")
