import asyncio
import random

from core.bot import Bot
from core.networking_client import connect
from core.enums import Direction


## My imports
import heapq
from math import inf


# Example Python 3 bot implementation for Planet Lia Bounce Evasion.
class MyBot(Bot):

    # Called only once before the match starts. It holds the
    # data that you may need before the game starts.
    def setup(self, initial_data):
        
        print(initial_data)
        self.initial_data = initial_data
        self.mapHeight = int(initial_data["mapHeight"])
        self.mapWidth = int(initial_data["mapWidth"])
        self.half_map_Width = int(self.mapWidth / 2)
        self.map = initial_data["map"]
        self.left_bot = False
        self.reset_player = False
        self.possible_moves = [Direction.LEFT, Direction.RIGHT, Direction.UP, Direction.DOWN]
        
        self.my_x = int(initial_data["yourUnit"]["x"])
        self.my_y = int(initial_data["yourUnit"]["y"])
        #self.my_part_of_map = [[False for i in range(0, self.half_map_Width)] for j in range(0, self.mapHeight)]
        if (self.my_x == 0):
            self.left_bot = True
        #    for y in range(0,self.mapHeight):
        #        for x in range(0, self.half_map_Width):
        #            self.my_part_of_map[y][x] = self.map[y][x]
        
        elif(self.my_x == 19):
            self.left_bot = False
        #    for y in range(0,self.mapHeight):
        #        for x in range(self.half_map_Width, self.mapWidth):
        #            self.my_part_of_map[y][x - self.half_map_Width] = self.map[y][x - self.half_map_Width]
        
        self.print_debug("::setup")
        # Print out the map
        self.print_part_of_map(self.map, self.mapHeight, self.mapWidth)
        #self.print_part_of_map(self.my_part_of_map, self.mapHeight, self.half_map_Width)
        
        self.Reset(self.initial_data["coins"])
        

    def print_debug(self, text):
        if (self.left_bot):
            print("BOT LEFT: " + str(text))
        else:
            print("BOT RIGHT: " + str(text))

    def print_part_of_map(self, mapy, mapHeight, mapWidth):
        for y in range(mapHeight - 1, -1, -1):
            map_line = ""
            for x in range(0, mapWidth):
                map_line += "_" if mapy[y][x] else "#"
            self.print_debug(map_line)

    def Reset(self, list_of_coins):
        self.print_debug("::Reset")
        self.closest_coin = self.ChooseClosestCoin(list_of_coins)
        self.print_debug("Closest coin: " + str(self.closest_coin))

        self.candidates = []
        self.costs_for_field = [[inf for _ in range(self.mapWidth)] for __ in range(self.mapHeight)]
        self.print_debug("My coordinate: " + str(self.my_x) + ", " + str(self.my_y))
        self.costs_for_field[self.my_y][self.my_x] = self.ManhatanDistance(self.my_x, self.my_y, self.closest_coin[0], self.closest_coin[1])    
        

        #self.PushCandidate((self.costs_for_field[self.my_y][self.my_x], (self.my_x, self.my_y)))
        #self.FindAllCandidates()

    def PushCandidate(self, elem):
        self.print_debug("::PushCandidate")
        heapq.heappush(self.candidates, elem)  
    def GetCandidate(self):
        self.print_debug("::GetCandidate")
        candidate = heapq.heappop(self.candidates)
        self.print_debug(candidate)
        return candidate

    def ChooseClosestCoin(self, list_of_coins):
        self.print_debug("::ChooseClosestCoin")
        possible_coins = []
        for coin in list_of_coins:
            x = coin["x"]
            if (self.left_bot):
                if(x <= self.half_map_Width):
                    possible_coins.append(coin)
            else:
                if(x > self.half_map_Width):
                    possible_coins.append(coin)
        if (len(possible_coins) == 0):
            # TODO: Improve this
            if(self.left_bot):
                while True:
                    coordinate = (random.randint(0, self.half_map_Width), random.randint(0, self.mapHeight))

                    if not (coordinate[0] >= 0 and coordinate[1] >= 0 and coordinate[0] < self.mapWidth and coordinate[1] < self.mapHeight):
                        continue

                    if (self.map[coordinate[1]][coordinate[0]]):
                        return coordinate
            else:
                while True:
                    coordinate = (random.randint(self.half_map_Width, self.mapWidth), random.randint(0, self.mapHeight))
                    
                    if not (coordinate[0] >= 0 and coordinate[1] >= 0 and coordinate[0] < self.mapWidth and coordinate[1] < self.mapHeight):
                        continue


                    if (self.map[coordinate[1]][coordinate[0]]):
                        return coordinate
                #return (-1, -1)
        elif (len(possible_coins) == 1):
            return (possible_coins[0]["x"],possible_coins[0]["y"]) 
        else:
            coin1_x = possible_coins[0]["x"]
            coin1_y = possible_coins[0]["y"]
            coin1_len = self.ManhatanDistance(self.my_x,self.my_y, coin1_x, coin1_y)

            coin2_x = possible_coins[1]["x"]
            coin2_y = possible_coins[1]["y"]
            coin2_len = self.ManhatanDistance(self.my_x,self.my_y, coin2_x, coin2_y)

            if (coin1_len < coin2_len):
                return (coin1_x, coin1_y)
            else:
                return (coin2_x, coin2_y)
        
    def ManhatanDistance(self, x1, y1, x2, y2):
        return abs(x1 - x2) + abs(y1 - y2)

    def FindAllCandidates(self):
        self.print_debug("::FindAllCandidates")

        
        for direction in self.possible_moves:
            d_dir = (0,0)
            if direction == Direction.LEFT:
                d_dir = (-1,0)
            elif direction == Direction.RIGHT:
                d_dir = (1,0)
            if direction == Direction.UP:
                d_dir = (0,1)
            elif direction == Direction.DOWN:
                d_dir = (0,-1)
            
            new_y = self.my_y + d_dir[1]
            new_x = self.my_x + d_dir[0]

            if not (new_x >= 0 and new_y >= 0 and new_x < self.mapWidth and new_y < self.mapHeight):
                continue
            
            # TODO: Check also for saws
            if (not self.map[new_y][new_x]):
                continue

            self.print_debug("New x:" + str(new_x) + " New y:" + str(new_y))
            curr_distance = self.ManhatanDistance(new_x, new_y, self.closest_coin[0], self.closest_coin[1])
            if (curr_distance < self.costs_for_field[new_y][new_x]):
                self.costs_for_field[new_y][new_x] = curr_distance
                self.PushCandidate((self.costs_for_field[new_y][new_x], direction))

    # Called repeatedly while the match is generating. Each
    # time you receive the current match state and can use
    # response object to issue your commands.
    def update(self, state, response):
        # Find and send your unit to a random direction that
        # moves it to a valid field on the map
        # TODO: Remove this code and implement a proper path finding!


        self.my_x = int(state["yourUnit"]["x"])
        self.my_y = int(state["yourUnit"]["y"])

        if (self.reset_player):
            self.reset_player = False
            self.Reset(state["coins"])
            # TODO: Dodaj se kaj kar bos rabu

        if (self.my_x == self.closest_coin[0] and  self.my_y == self.closest_coin[1]):
            self.print_debug("Dobil kovanec!!!")
            self.reset_player = True
            
        self.FindAllCandidates()

        closest_candidate = self.GetCandidate()
        candidate_cost = closest_candidate[0]
        candidate_move = closest_candidate[1]
        response.move_unit(candidate_move)

# Connects your bot to match generator, don't change it.
if __name__ == "__main__":
    asyncio.get_event_loop().run_until_complete(connect(MyBot()))
