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
        
        
        self.my_x = int(initial_data["yourUnit"]["x"])
        self.my_y = int(initial_data["yourUnit"]["y"])
        self.my_part_of_map = [[False for i in range(0, self.half_map_Width)] for j in range(0, self.mapHeight)]
        if (self.my_x == 0):
            self.left_bot = True
            for y in range(0,self.mapHeight):
                for x in range(0, self.half_map_Width):
                    self.my_part_of_map[y][x] = self.map[y][x]
        
        elif(self.my_x == 19):
            self.left_bot = False
            for y in range(0,self.mapHeight):
                for x in range(self.half_map_Width, self.mapWidth):
                    self.my_part_of_map[y][x - self.half_map_Width] = self.map[y][x - self.half_map_Width]

        # Print out the map
        self.print_part_of_map(self.map, self.mapHeight, self.mapWidth)
        self.print_part_of_map(self.my_part_of_map, self.mapHeight, self.half_map_Width)
        self.closest_coin = self.ChooseClosestCoin(initial_data["coins"])
        self.print_debug(self.closest_coin)

        self.Reset()

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

    def Reset(self):
        self.candidates = []
        self.costs_for_field = [[inf for _ in range(self.half_map_Width)] for __ in range(self.mapHeight)]
        self.costs_for_field[self.my_y][self.my_x] = self.ManhatanDistance(self.my_x,self.my_y, self.closest_coin[0], self.closest_coin[1])
        self.candidates.append(self.my_x, self.my_y)

    def PushCandidate(self, elem):
        heapq.heappush(self.candidates, elem)  

    def ChooseClosestCoin(self, list_of_coins):
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
            return (-1, -1)
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
        
    def ManhatanDistance(x1, y1, x2, y2):
        return abs(x1 - x2) + abs(y1 - y2)

    # Called repeatedly while the match is generating. Each
    # time you receive the current match state and can use
    # response object to issue your commands.
    def update(self, state, response):
        # Find and send your unit to a random direction that
        # moves it to a valid field on the map
        # TODO: Remove this code and implement a proper path finding!
        while True:
            r = random.randint(0, 3)

            # Pick a random direction to move
            if r == 0:
                direction = Direction.LEFT
            elif r == 1:
                direction = Direction.RIGHT
            elif r == 2:
                direction = Direction.UP
            else:
                direction = Direction.DOWN

            # Find on which position this move will send your unit
            new_x = state["yourUnit"]["x"]
            new_y = state["yourUnit"]["y"]
            if direction == Direction.LEFT:
                new_x -= 1
            elif direction == Direction.RIGHT:
                new_x += 1
            if direction == Direction.UP:
                new_y += 1
            elif direction == Direction.DOWN:
                new_y -= 1

            # If the new position is on the map then send the unit towards
            # that direction and break the loop, else try again
            map_width = self.initial_data["mapWidth"]
            map_height = self.initial_data["mapHeight"]
            if new_x >= 0 and new_y >= 0 and new_x < map_width and new_y < map_height \
                    and self.initial_data["map"][new_y][new_x]:
                response.move_unit(direction)
                break

# Connects your bot to match generator, don't change it.
if __name__ == "__main__":
    asyncio.get_event_loop().run_until_complete(connect(MyBot()))
