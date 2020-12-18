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
rm results/first_player/*
rm results/second_player/*

for MAX_DEPTH in 8
do
for WEIGHT_OF_GAME_ORVER_SCORE in 0
do
for WEIGHT_OF_FIRST_HEURISTIC in 0 1
do
for WEIGHT_OF_SECOND_HEURISTIC in 0 1
do
for WEIGHT_OF_THIRD_HEURISTIC in 0 1
do
for WEIGHT_OF_FORTH_HEURISTIC in 0 1
do
for WEIGHT_OF_FIFTH_HEURISTIC in 0 1
do
  export AGENT_RUN_WITH_ARGS="java -jar target/minimaxBot-1.0.1.jar $MAX_DEPTH $WEIGHT_OF_GAME_ORVER_SCORE $WEIGHT_OF_FIRST_HEURISTIC $WEIGHT_OF_SECOND_HEURISTIC $WEIGHT_OF_THIRD_HEURISTIC $WEIGHT_OF_FORTH_HEURISTIC $WEIGHT_OF_FIFTH_HEURISTIC"
#	if [[ $* == *--second* ]]
#  then
#    java -jar resources/ManKalah.jar "java -jar resources/Test_Agents/error404.jar" "$AGENT_RUN_WITH_ARGS" >  results/second_player/$MAX_DEPTH-$WEIGHT_OF_GAME_ORVER_SCORE-$WEIGHT_OF_FIRST_HEURISTIC-$WEIGHT_OF_SECOND_HEURISTIC.log
#  else
##    OUTPUT=$(java -jar resources/ManKalah.jar "$AGENT_RUN_WITH_ARGS" "java -jar resources/Test_Agents/Group2Agent.jar")
##    java -jar resources/ManKalah.jar "$AGENT_RUN_WITH_ARGS" "java -jar resources/Test_Agents/Group2Agent.jar" >  results/$MAX_DEPTH-$WEIGHT_OF_GAME_ORVER_SCORE-$WEIGHT_OF_FIRST_HEURISTIC-$WEIGHT_OF_SECOND_HEURISTIC.log
#    java -jar resources/ManKalah.jar "$AGENT_RUN_WITH_ARGS" "java -jar resources/Test_Agents/error404.jar" >  results/first_player/$MAX_DEPTH-$WEIGHT_OF_GAME_ORVER_SCORE-$WEIGHT_OF_FIRST_HEURISTIC-$WEIGHT_OF_SECOND_HEURISTIC.log
#  fi
    java -jar resources/ManKalah.jar "java -jar resources/Test_Agents/error404.jar" "$AGENT_RUN_WITH_ARGS" >  results/second_player/error404$MAX_DEPTH-$WEIGHT_OF_GAME_ORVER_SCORE-$WEIGHT_OF_FIRST_HEURISTIC-$WEIGHT_OF_SECOND_HEURISTIC-$WEIGHT_OF_THIRD_HEURISTIC-$WEIGHT_OF_FORTH_HEURISTIC-$WEIGHT_OF_FIFTH_HEURISTIC.log
    java -jar resources/ManKalah.jar "$AGENT_RUN_WITH_ARGS" "java -jar resources/Test_Agents/error404.jar" >  results/first_player/error404$MAX_DEPTH-$WEIGHT_OF_GAME_ORVER_SCORE-$WEIGHT_OF_FIRST_HEURISTIC-$WEIGHT_OF_SECOND_HEURISTIC-$WEIGHT_OF_THIRD_HEURISTIC-$WEIGHT_OF_FORTH_HEURISTIC-$WEIGHT_OF_FIFTH_HEURISTIC.log

    java -jar resources/ManKalah.jar "java -jar resources/Test_Agents/Group2Agent.jar" "$AGENT_RUN_WITH_ARGS" >  results/second_player/Group2Agent$MAX_DEPTH-$WEIGHT_OF_GAME_ORVER_SCORE-$WEIGHT_OF_FIRST_HEURISTIC-$WEIGHT_OF_SECOND_HEURISTIC-$WEIGHT_OF_THIRD_HEURISTIC-$WEIGHT_OF_FORTH_HEURISTIC-$WEIGHT_OF_FIFTH_HEURISTIC.log
    java -jar resources/ManKalah.jar "$AGENT_RUN_WITH_ARGS" "java -jar resources/Test_Agents/Group2Agent.jar" >  results/first_player/Group2Agent$MAX_DEPTH-$WEIGHT_OF_GAME_ORVER_SCORE-$WEIGHT_OF_FIRST_HEURISTIC-$WEIGHT_OF_SECOND_HEURISTIC-$WEIGHT_OF_THIRD_HEURISTIC-$WEIGHT_OF_FORTH_HEURISTIC-$WEIGHT_OF_FIFTH_HEURISTIC.log

    java -jar resources/ManKalah.jar "java -jar resources/Test_Agents/JimmyPlayer.jar" "$AGENT_RUN_WITH_ARGS" >  results/second_player/JimmyPlayer$MAX_DEPTH-$WEIGHT_OF_GAME_ORVER_SCORE-$WEIGHT_OF_FIRST_HEURISTIC-$WEIGHT_OF_SECOND_HEURISTIC-$WEIGHT_OF_THIRD_HEURISTIC-$WEIGHT_OF_FORTH_HEURISTIC-$WEIGHT_OF_FIFTH_HEURISTIC.log
    java -jar resources/ManKalah.jar "$AGENT_RUN_WITH_ARGS" "java -jar resources/Test_Agents/JimmyPlayer.jar" >  results/first_player/JimmyPlayer$MAX_DEPTH-$WEIGHT_OF_GAME_ORVER_SCORE-$WEIGHT_OF_FIRST_HEURISTIC-$WEIGHT_OF_SECOND_HEURISTIC-$WEIGHT_OF_THIRD_HEURISTIC-$WEIGHT_OF_FORTH_HEURISTIC-$WEIGHT_OF_FIFTH_HEURISTIC.log
done
done
done
done
done
done
done

python countResults.py
