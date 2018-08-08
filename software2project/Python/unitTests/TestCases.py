import unittest
from Python import CPUTest, IntegerMathTest, MemoryTest, NetworkingTest, StorageTest, VideoTest

class TestCases(unittest.TestCase):

    def test_CPU(self):
        print("Now testing the CPU Test module...")
        result = CPUTest.main()
        self.assertEqual(result, "SUCCESS")

    def test_Int(self):
        print("Now testing the Integer Test module...")
        result = IntegerMathTest.main()
        self.assertEqual(result, "SUCCESS")

    def test_Mem(self):
        print("Now testing the Memory Test module...")
        result = MemoryTest.main()
        self.assertEqual(result, "SUCCESS")

    def test_Net(self):
        print("Now testing the Networking Test module...")
        result = NetworkingTest.main()
        self.assertEqual(result, "SUCCESS")

    def test_Storage(self):
        print("Now testing the Integer Test module...")
        result = StorageTest.main()
        self.assertEqual(result, "SUCCESS")

    def test_Video(self):
        print("Now testing the Integer Test module...")
        result = VideoTest.main()
        self.assertEqual(result, "SUCCESS")

if __name__ == '__main__':
    unittest.main()
