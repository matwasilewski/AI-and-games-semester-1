if [[ $* == *-b* ]]
then
  echo "Building package"
  mvn clean compile jar:jar
fi

#- intArg1 - max depth of minimax tree
#- intArg2 - weight of game over score function
#- intArg3 - weight of first heuristic
#- intArg4 - weight of second heuristic
rm heuristic_results/test/* -r

for MAX_DEPTH in 10
do
for WEIGHT_OF_GAME_ORVER_SCORE in 0 3
do
for WEIGHT_OF_FIRST_HEURISTIC in 0 1 2
do
for WEIGHT_OF_SECOND_HEURISTIC in 0 1 2
do
  export AGENT_RUN_WITH_ARGS1="java -jar target/minimaxBot-1.0.1.jar $MAX_DEPTH $WEIGHT_OF_GAME_ORVER_SCORE $WEIGHT_OF_FIRST_HEURISTIC $WEIGHT_OF_SECOND_HEURISTIC"
  
  mkdir "heuristic_results/test/$MAX_DEPTH-$WEIGHT_OF_GAME_ORVER_SCORE-$WEIGHT_OF_FIRST_HEURISTIC-$WEIGHT_OF_SECOND_HEURISTIC"
  mkdir "heuristic_results/test/$MAX_DEPTH-$WEIGHT_OF_GAME_ORVER_SCORE-$WEIGHT_OF_FIRST_HEURISTIC-$WEIGHT_OF_SECOND_HEURISTIC/second_player"
  mkdir "heuristic_results/test/$MAX_DEPTH-$WEIGHT_OF_GAME_ORVER_SCORE-$WEIGHT_OF_FIRST_HEURISTIC-$WEIGHT_OF_SECOND_HEURISTIC/first_player"

  for MAX_DEPTH2 in 10
  do
  for WEIGHT_OF_GAME_ORVER_SCORE2 in 0 3
  do
  for WEIGHT_OF_FIRST_HEURISTIC2 in 0 1 2
  do
  for WEIGHT_OF_SECOND_HEURISTIC2 in 0 1 2
  do
    export AGENT_RUN_WITH_ARGS2="java -jar target/minimaxBot-1.0.1.jar $MAX_DEPTH2 $WEIGHT_OF_GAME_ORVER_SCORE2 $WEIGHT_OF_FIRST_HEURISTIC2 $WEIGHT_OF_SECOND_HEURISTIC2"
      java -jar resources/ManKalah.jar "$AGENT_RUN_WITH_ARGS1" "$AGENT_RUN_WITH_ARGS2" >  heuristic_results/test/$MAX_DEPTH-$WEIGHT_OF_GAME_ORVER_SCORE-$WEIGHT_OF_FIRST_HEURISTIC-$WEIGHT_OF_SECOND_HEURISTIC/first_player/$MAX_DEPTH2-$WEIGHT_OF_GAME_ORVER_SCORE2-$WEIGHT_OF_FIRST_HEURISTIC2-$WEIGHT_OF_SECOND_HEURISTIC2.log
      java -jar resources/ManKalah.jar "$AGENT_RUN_WITH_ARGS2" "$AGENT_RUN_WITH_ARGS1" >  heuristic_results/test/$MAX_DEPTH-$WEIGHT_OF_GAME_ORVER_SCORE-$WEIGHT_OF_FIRST_HEURISTIC-$WEIGHT_OF_SECOND_HEURISTIC/second_player/$MAX_DEPTH2-$WEIGHT_OF_GAME_ORVER_SCORE2-$WEIGHT_OF_FIRST_HEURISTIC2-$WEIGHT_OF_SECOND_HEURISTIC2.log
  done
  done
  done
  done

done
done
done
done

python countResultsHeuristics.py
