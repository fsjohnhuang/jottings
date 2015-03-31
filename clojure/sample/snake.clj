(ns sample.snake
  (:import (java.awt Color Dimension)
           (javax.swing JPanel JFrame Timer JOptionPane)
           (java.awt.event ActionListener KeyListener)))

(def width 200)
(def height 400)
(def point-size 10)

(defn create-panel [frame]
  (proxy [JPanel] []
    (getPreferredSize []
      (Dimension. (* width point-size) (* height point-size)))))

(defn start []
  (let [frame (JFrame. "Snake")
        panel (create-panel frame)]
    (doto frame 
      (.add panel)
      (.pack)
      (.setVisible true))))

(start)

