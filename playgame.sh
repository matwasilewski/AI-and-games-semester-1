if [[ $* == *-b* ]]
then
  echo "Building package"
  mvn package
fi

if [[ $* == *-j* || $* == *-a* ]]
then
  if [[ $* == *--second* ]]
  then
    java -jar resources/ManKalah.jar "java -jar resources/Test_Agents/JimmyPlayer.jar" "java -jar target/minimaxBot-1.0.1.jar"
  else
    java -jar resources/ManKalah.jar "java -jar target/minimaxBot-1.0.1.jar" "java -jar resources/Test_Agents/JimmyPlayer.jar"
  fi
fi

if [[ $* == *-g* || $* == *-a* ]]
then
  if [[ $* == *--second* ]]
  then
    java -jar resources/ManKalah.jar "java -jar resources/Test_Agents/Group2Agent.jar" "java -jar target/minimaxBot-1.0.1.jar"
  else
    java -jar resources/ManKalah.jar "java -jar target/minimaxBot-1.0.1.jar" "java -jar resources/Test_Agents/Group2Agent.jar"
  fi
fi
