#include <vector>
#include <mutex>
#include <cstring>
#include <algorithm>
#include <stdexcept>

class RingBuffer {
private:
    std::vector<char> buffer;
    size_t capacity;
    size_t head;
    size_t tail;
    size_t size;
    std::mutex mtx;

public:
    explicit RingBuffer(size_t cap = 65536)
            : buffer(cap),
              capacity(cap),
              head(0),
              tail(0),
              size(0) {}

    size_t available() const {
        return size;
    }

    size_t freeSpace() const {
        return capacity - size;
    }

    void clear() {
        std::lock_guard<std::mutex> lock(mtx);
        head = 0;
        tail = 0;
        size = 0;
    }

    size_t write(const char* data, size_t len) {
        std::lock_guard<std::mutex> lock(mtx);

        if (len > freeSpace()) {
            len = freeSpace(); // prevent overflow
        }

        size_t written = 0;

        while (written < len) {
            buffer[tail] = data[written];
            tail = (tail + 1) % capacity;
            written++;
        }

        size += written;
        return written;
    }

    size_t read(char* out, size_t len) {
        std::lock_guard<std::mutex> lock(mtx);

        if (len > size) {
            len = size;
        }

        size_t readBytes = 0;

        while (readBytes < len) {
            out[readBytes] = buffer[head];
            head = (head + 1) % capacity;
            readBytes++;
        }

        size -= readBytes;
        return readBytes;
    }

    bool peek(char* out, size_t len) {
        std::lock_guard<std::mutex> lock(mtx);

        if (len > size) return false;

        size_t tempHead = head;

        for (size_t i = 0; i < len; i++) {
            out[i] = buffer[tempHead];
            tempHead = (tempHead + 1) % capacity;
        }

        return true;
    }
};// Placeholder for app/src/main/cpp/core/buffer.cpp
