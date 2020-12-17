import glob
import os
import collections

output = []
statistics = collections.defaultdict(collections.defaultdict)

for opponent in ['error404', 'Group2Agent', 'JimmyPlayer']:
   for playerPossition in ['first_player', 'second_player']:
      for filename in glob.glob('results/'+playerPossition+'/'+opponent+'*.log'):
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

            filename_str = filename.replace('-', ' ').replace('results/', '').replace('.log', '').replace(playerPossition + '/', '')
            output.append((playerPossition, filename_str, win, time))

output.sort(key=lambda x: (x[0], x[1]))
with open(os.path.join(os.getcwd(), "results/results.txt"), 'w') as output_file:
   for playerPossition, filename_str,win,time in output:
      output_line = f"Possition: {playerPossition} Arguments: {filename_str} Result: {win} Time: {time}"
      output_file.write(output_line + "\n")