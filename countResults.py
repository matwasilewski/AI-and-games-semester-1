import glob
import os
import collections

output = []
statistics = collections.defaultdict(collections.defaultdict)

for filename in glob.glob('results/*.log'):
   with open(os.path.join(os.getcwd(), filename), 'r') as f: # open in readonly mode
      data = f.read()
      data_arr = data.split("\n")

      agent = data_arr[0].split(" ")
      opponent = data_arr[1].split(" ")
      time = agent[1]
      if agent[0] == '1':
         win = "WON"
      else:
         win = "LOST"

      filename_str = filename.replace('-', ' ').replace('results/', '').replace('.log', '')
      output.append((filename_str, win, time))

output.sort()
with open(os.path.join(os.getcwd(), "results/results.txt"), 'w') as output_file:
   for filename_str,win,time in output:
      output_line = f"Arguments: {filename_str} Result: {win} Time: {time}"
      output_file.write(output_line + "\n")