(ns game-of-life-test
  (:require [clojure.test :refer :all]
            [game_of_life :refer :all]))

(deftest neighbors-of-test
  (is (= #{[1 1] [2 1] [3 1]
           [1 2]       [3 2]
           [1 3] [2 3] [3 3]} (neighbors-of [2 2])))
  (is (= #{[-3 -3] [-2 -3] [-1 -3]
           [-3 -2]         [-1 -2]
           [-3 -1] [-2 -1] [-1 -1]} (neighbors-of [-2 -2]))))

(deftest living-neighbors-test
  (is (= 0 (living-neighbors [2 2] #{})))
  (is (= 0 (living-neighbors [2 2] #{[0 0] [1 0] [2 0] [3 0] [4 0]
                                     [0 1]                   [4 1]
                                     [0 2]                   [4 2]
                                     [0 3]                   [4 3]
                                     [0 4] [1 4] [2 4] [3 4] [4 4]})))
  (is (= 1 (living-neighbors [2 2] #{[1 2]}))) 
  (is (= 5 (living-neighbors [2 2] #{[1 1]     [1 2] 
                                     [2 3]
                                     [3 1]     [3 3]})))
  (is (= 8 (living-neighbors [2 2] (neighbors-of [2 2])))))

(deftest will-live-test
  (is (= false (will-live? [2 2] #{})))
  (is (= false (will-live? [2 2] #{[2 3]})))
  (is (= false (will-live? [2 2] #{[1 2]       [3 2]})))
  (is (= true  (will-live? [2 2] #{      [2 1]      
                                   [1 2]       [3 2]})))
  (is (= false (will-live? [2 2] #{      [2 1]
                                   [1 2]       [3 2] 
                                         [2 3]})))
  (is (= false (will-live? [2 2] #{[2 2]})))
  (is (= false (will-live? [2 2] #{[1 2] [2 2]})))
  (is (= true  (will-live? [2 2] #{[1 2] [2 2] [3 2]})))
  (is (= true  (will-live? [2 2] #{      [2 1]
                                   [1 2] [2 2] [3 2]})))
  (is (= false (will-live? [2 2] #{      [2 1]
                                   [1 2] [2 2] [3 2]
                                         [2 3]})))
  
  (is (= false (will-live? [2 2] (set (neighbors-of [-2 -2])))))
  )


(deftest next-generation-test
  (is (= #{} (next-generation #{[2 2]})))
  (is (= #{[2 1]
           [2 2]
           [2 3]} (next-generation #{[1 2] [2 2] [3 2]})))
  (is (= #{[1 2] [2 2] [3 2]} (next-generation #{[2 1]
                                                 [2 2]
                                                 [2 3]})))
  ;; [x] [] [x] [x] ->  [] [] [x] [x]
  ;; [x] [] [x] [ ]     [] [] [x] [x]
  (is (= #{[3 1] [4 1] [3 2] [4 2]} (next-generation (set '([1 1] [1 2] [3 1] [3 2] [4 2]))))))

(deftest board-to-string-test
  ;error: cannot run without any living
  ;(is (= "" (board-to-string #{})))
  (is (= "#"             (board-to-string #{[2 2]})))
  (is (= "-#\n#-"        (board-to-string #{[1 1]
                                                  [2 2]})))
  (is (= "#-\n-#"        (board-to-string #{      [2 1]
                                            [1 2]      })))
  (is (= "##\n##"        (board-to-string #{[1 1] [2 1]
                                            [1 2] [2 2]})))
  (is (= "#-#\n---\n#-#" (board-to-string #{[1 1]       [3 1]
                                                            
                                            [1 3]       [3 3]})))
  (is (= "#-##\n#-#-"    (board-to-string #{[1 1]       [3 1] 
                                            [1 2]       [3 2] [4 2]}))))

(deftest string-to-board-test 
  (is (= #{[1 1] [1 2] [3 1] [3 2] [4 2]} (string-to-board (board-to-string #{[1 1] [1 2] [3 1] [3 2] [4 2]})))))

(defn tests [opts]
  (run-all-tests #"game-of-life-test"))

;; to run:
;; clj -X <file>/<function>

;; to test:
;; clj -X:test