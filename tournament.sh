if [[ $* == *-b* ]]
then
  echo "Building package"
  mvn clean compile jar:jar
fi

#- intArg1 - max depth of minimax tree
#- intArg2 - weight of game over score function
#- intArg3 - weight of first heuristic
#- intArg4 - weight of second heuristic
rm results/*

for MAX_DEPTH in 10
do
for WEIGHT_OF_GAME_ORVER_SCORE in 0 1 2 3 4 5 6
do
for WEIGHT_OF_FIRST_HEURISTIC in 0 1
do
for WEIGHT_OF_SECOND_HEURISTIC in 0 1
do
  export AGENT_RUN_WITH_ARGS="java -jar target/minimaxBot-1.0.1.jar $MAX_DEPTH $WEIGHT_OF_GAME_ORVER_SCORE $WEIGHT_OF_FIRST_HEURISTIC $WEIGHT_OF_SECOND_HEURISTIC"
	if [[ $* == *--second* ]]
  then
    java -jar resources/ManKalah.jar "java -jar resources/Test_Agents/Group2Agent.jar" "$AGENT_RUN_WITH_ARGS"
  else
#    OUTPUT=$(java -jar resources/ManKalah.jar "$AGENT_RUN_WITH_ARGS" "java -jar resources/Test_Agents/Group2Agent.jar")
#    java -jar resources/ManKalah.jar "$AGENT_RUN_WITH_ARGS" "java -jar resources/Test_Agents/Group2Agent.jar" >  results/$MAX_DEPTH-$WEIGHT_OF_GAME_ORVER_SCORE-$WEIGHT_OF_FIRST_HEURISTIC-$WEIGHT_OF_SECOND_HEURISTIC.log
    java -jar resources/ManKalah.jar "$AGENT_RUN_WITH_ARGS" "java -jar resources/Test_Agents/error404.jar" >  results/$MAX_DEPTH-$WEIGHT_OF_GAME_ORVER_SCORE-$WEIGHT_OF_FIRST_HEURISTIC-$WEIGHT_OF_SECOND_HEURISTIC.log
  fi
done
done
done
done

python countResults.py