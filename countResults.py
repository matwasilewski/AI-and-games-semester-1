import glob
import os
import collections

output = []
statistics = collections.defaultdict(lambda: [0,0,0])

for opponent_name in ['error404', 'Group2Agent', 'JimmyPlayer','heuristic1']:
   for playerPossition in ['first_player', 'second_player']:
      for filename in glob.glob('results/'+playerPossition+'/'+opponent_name+'*.log'):
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

            filename_str = filename.replace('-', ' ').replace('results/', '').replace('.log', '').replace(playerPossition + '/', '').replace(opponent_name, '')

            statistics[filename_str][1] += 1
            statistics[filename_str][2] += int(time)

            if win == "WON":
               statistics[filename_str][0] += 1
               output.append((opponent_name, playerPossition, filename_str, win, time))

output.sort()
with open(os.path.join(os.getcwd(), "results/results.txt"), 'w') as output_file:
   for opponent_name, playerPossition, filename_str, win, time in output:
      output_line = f"Opponent: {opponent_name} Possition: {playerPossition} Arguments: {filename_str} Result: {win} Time: {time}"
      output_file.write(output_line + "\n")

statistics_arr = [[x,statistics[x]] for x in statistics]
statistics_arr.sort(key=lambda x: x[1])
# how many times player won
with open(os.path.join(os.getcwd(), "results/results2.txt"), 'w') as output_file:
   for player, statisc in statistics_arr:
      total_won, total_played, total_time = statisc
      avg_time = total_time/total_played
      output_line = f"Agent: {player} Total won: {total_won} Total played: {total_played} Avg Time: {avg_time}"
      output_file.write(output_line + "\n")