import glob
import os
import collections

output = []

for player in glob.glob('heuristic_results/test/*/'):
   player_name = player.replace("heuristic_results/test/", '').replace('/', '')
   total_win = 0
   total_played = 0
   total_time = 0
   for playerPossition in ['first_player', 'second_player']:
      for filename in glob.glob(player+playerPossition+'/'+'*.log'):
         with open(os.path.join(os.getcwd(), filename), 'r') as f: # open in readonly mode
            data = f.read()
            data_arr = data.split("\n")

            agent = data_arr[0].split(" ")
            opponent = data_arr[1].split(" ")
            if playerPossition == 'first_player':
               time = agent[1]
               if agent[0] == '1':
                  win = "WON"
               else:
                  win = "LOST"
            else:
               time = opponent[1]
               if opponent[0] == '1':
                  win = "WON"
               else:
                  win = "LOST"

            if win == "WON":
               total_win += 1

            total_played += 1
            total_time += int(time)

   output.append((total_win, total_time, total_played, player_name))

output.sort()
with open(os.path.join(os.getcwd(), "heuristic_results/results.txt"), 'w') as output_file:
   for total_win, total_time, total_played, player_name in output:
      average_time = total_time/total_played
      output_line = f"Player: {player_name} Total win: {total_win} Total played: {total_played} Avg time: {average_time}"
      output_file.write(output_line + "\n")