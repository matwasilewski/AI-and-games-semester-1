if [[ $* == *-b* ]]
then
  echo "Building package"
  mvn clean compile jar:jar
fi

export AGENT_RUN_WITH_ARGS="java -jar target/minimaxBot-1.0.1.jar 10 5 1 1"

if [[ $* == *-j* || $* == *-a* ]]
then
  if [[ $* == *--second* ]]
  then
    java -jar resources/ManKalah.jar "java -jar resources/Test_Agents/JimmyPlayer.jar" "$AGENT_RUN_WITH_ARGS"
  else
    java -jar resources/ManKalah.jar "$AGENT_RUN_WITH_ARGS" "java -jar resources/Test_Agents/JimmyPlayer.jar"
  fi
fi

if [[ $* == *-g* || $* == *-a* ]]
then
  if [[ $* == *--second* ]]
  then
    java -jar resources/ManKalah.jar "java -jar resources/Test_Agents/Group2Agent.jar" "$AGENT_RUN_WITH_ARGS"
  else
    java -jar resources/ManKalah.jar "$AGENT_RUN_WITH_ARGS" "java -jar resources/Test_Agents/Group2Agent.jar"
  fi
fi
